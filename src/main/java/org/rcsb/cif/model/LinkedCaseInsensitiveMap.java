/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.rcsb.cif.model;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * {@link LinkedHashMap} variant that stores String keys in a case-insensitive
 * manner, for example for key-based access in a results table.
 *
 * <p>Preserves the original order as well as the original casing of keys,
 * while allowing for contains, get and remove calls with any case of key.
 *
 * <p>Does <i>not</i> support {@code null} keys.
 *
 * Part of the spring-framework but really we need just this one class.
 *
 * @author Juergen Hoeller
 * @author Phillip Webb
 * @since 3.0
 * @param <V> the value type
 */
public class LinkedCaseInsensitiveMap<V> implements Map<String, V>, Serializable, Cloneable {
    private final LinkedHashMap<String, V> targetMap;
    private final HashMap<String, String> caseInsensitiveKeys;
    private final Locale locale;

    private transient volatile Set<String> keySet;
    private transient volatile Collection<V> values;
    private transient volatile Set<Entry<String, V>> entrySet;


    /**
     * Create a new LinkedCaseInsensitiveMap that stores case-insensitive keys
     * according to the default Locale (by default in lower case).
     * @see #convertKey(String)
     */
    public LinkedCaseInsensitiveMap() {
        this((Locale) null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that stores case-insensitive keys
     * according to the given Locale (by default in lower case).
     * @param locale the Locale to use for case-insensitive key conversion
     * @see #convertKey(String)
     */
    public LinkedCaseInsensitiveMap(Locale locale) {
        this(16, locale);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores case-insensitive keys
     * according to the default Locale (by default in lower case).
     * @param initialCapacity the initial capacity
     * @see #convertKey(String)
     */
    public LinkedCaseInsensitiveMap(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores case-insensitive keys
     * according to the given Locale (by default in lower case).
     * @param initialCapacity the initial capacity
     * @param locale the Locale to use for case-insensitive key conversion
     * @see #convertKey(String)
     */
    public LinkedCaseInsensitiveMap(int initialCapacity, Locale locale) {
        this.targetMap = new LinkedHashMap<String, V>(initialCapacity) {
            @Override
            public boolean containsKey(Object key) {
                return LinkedCaseInsensitiveMap.this.containsKey(key);
            }
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
                boolean doRemove = LinkedCaseInsensitiveMap.this.removeEldestEntry(eldest);
                if (doRemove) {
                    removeCaseInsensitiveKey(eldest.getKey());
                }
                return doRemove;
            }
        };
        this.caseInsensitiveKeys = new HashMap<>(initialCapacity);
        this.locale = (locale != null ? locale : Locale.getDefault());
    }

    /**
     * Copy constructor.
     */
    @SuppressWarnings("unchecked")
    private LinkedCaseInsensitiveMap(LinkedCaseInsensitiveMap<V> other) {
        this.targetMap = (LinkedHashMap<String, V>) other.targetMap.clone();
        this.caseInsensitiveKeys = (HashMap<String, String>) other.caseInsensitiveKeys.clone();
        this.locale = other.locale;
    }

    // Implementation of java.util.Map

    @Override
    public int size() {
        return this.targetMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return (key instanceof String && this.caseInsensitiveKeys.containsKey(convertKey((String) key)));
    }

    @Override
    public boolean containsValue(Object value) {
        return this.targetMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.get(convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.get(caseInsensitiveKey);
            }
        }
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.get(convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.get(caseInsensitiveKey);
            }
        }
        return defaultValue;
    }

    @Override
    public V put(String key, V value) {
        String oldKey = this.caseInsensitiveKeys.put(convertKey(key), key);
        V oldKeyValue = null;
        if (oldKey != null && !oldKey.equals(key)) {
            oldKeyValue = this.targetMap.remove(oldKey);
        }
        V oldValue = this.targetMap.put(key, value);
        return (oldKeyValue != null ? oldKeyValue : oldValue);
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        if (map.isEmpty()) {
            return;
        }
        map.forEach(this::put);
    }

    @Override
    public V putIfAbsent(String key, V value) {
        String oldKey = this.caseInsensitiveKeys.putIfAbsent(convertKey(key), key);
        if (oldKey != null) {
            return this.targetMap.get(oldKey);
        }
        return this.targetMap.putIfAbsent(key, value);
    }

    @Override
    public V computeIfAbsent(String key, Function<? super String, ? extends V> mappingFunction) {
        String oldKey = this.caseInsensitiveKeys.putIfAbsent(convertKey(key), key);
        if (oldKey != null) {
            return this.targetMap.get(oldKey);
        }
        return this.targetMap.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V remove(Object key) {
        if (key instanceof String) {
            String caseInsensitiveKey = removeCaseInsensitiveKey((String) key);
            if (caseInsensitiveKey != null) {
                return this.targetMap.remove(caseInsensitiveKey);
            }
        }
        return null;
    }

    @Override
    public void clear() {
        this.caseInsensitiveKeys.clear();
        this.targetMap.clear();
    }

    @Override
    public Set<String> keySet() {
        Set<String> keySet = this.keySet;
        if (keySet == null) {
            keySet = new KeySet(this.targetMap.keySet());
            this.keySet = keySet;
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = this.values;
        if (values == null) {
            values = new Values(this.targetMap.values());
            this.values = values;
        }
        return values;
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        Set<Entry<String, V>> entrySet = this.entrySet;
        if (entrySet == null) {
            entrySet = new EntrySet(this.targetMap.entrySet());
            this.entrySet = entrySet;
        }
        return entrySet;
    }

    @Override
    public LinkedCaseInsensitiveMap<V> clone() {
        return new LinkedCaseInsensitiveMap<>(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedCaseInsensitiveMap<?> that = (LinkedCaseInsensitiveMap<?>) o;
        return Objects.equals(targetMap, that.targetMap) &&
                Objects.equals(caseInsensitiveKeys, that.caseInsensitiveKeys) &&
                Objects.equals(locale, that.locale) &&
                Objects.equals(keySet, that.keySet) &&
                Objects.equals(values, that.values) &&
                Objects.equals(entrySet, that.entrySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetMap, caseInsensitiveKeys, locale, keySet, values, entrySet);
    }

    @Override
    public String toString() {
        return this.targetMap.toString();
    }

    // Specific to LinkedCaseInsensitiveMap

    /**
     * Return the locale used by this {@code LinkedCaseInsensitiveMap}.
     * Used for case-insensitive key conversion.
     * @since 4.3.10
     * @see #LinkedCaseInsensitiveMap(Locale)
     * @see #convertKey(String)
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Convert the given key to a case-insensitive key.
     * <p>The default implementation converts the key
     * to lower-case according to this Map's Locale.
     * @param key the user-specified key
     * @return the key to use for storing
     * @see String#toLowerCase(Locale)
     */
    private String convertKey(String key) {
        return key.toLowerCase(getLocale());
    }

    /**
     * Determine whether this map should remove the given eldest entry.
     * @param eldest the candidate entry
     * @return {@code true} for removing it, {@code false} for keeping it
     */
    private boolean removeEldestEntry(Map.Entry<String, V> eldest) {
        return false;
    }

    private String removeCaseInsensitiveKey(String key) {
        return this.caseInsensitiveKeys.remove(convertKey(key));
    }

    class KeySet extends AbstractSet<String> {
        private final Set<String> delegate;

        KeySet(Set<String> delegate) {
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public boolean contains(Object o) {
            return this.delegate.contains(o);
        }

        @Override
        public Iterator<String> iterator() {
            return new KeySetIterator();
        }

        @Override
        public boolean remove(Object o) {
            return LinkedCaseInsensitiveMap.this.remove(o) != null;
        }

        @Override
        public void clear() {
            LinkedCaseInsensitiveMap.this.clear();
        }

        @Override
        public Spliterator<String> spliterator() {
            return this.delegate.spliterator();
        }

        @Override
        public void forEach(Consumer<? super String> action) {
            this.delegate.forEach(action);
        }
    }

    class Values extends AbstractCollection<V> {
        private final Collection<V> delegate;

        Values(Collection<V> delegate) {
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public boolean contains(Object o) {
            return this.delegate.contains(o);
        }

        @Override
        public Iterator<V> iterator() {
            return new ValuesIterator();
        }

        @Override
        public void clear() {
            LinkedCaseInsensitiveMap.this.clear();
        }

        @Override
        public Spliterator<V> spliterator() {
            return this.delegate.spliterator();
        }

        @Override
        public void forEach(Consumer<? super V> action) {
            this.delegate.forEach(action);
        }
    }

    class EntrySet extends AbstractSet<Entry<String, V>> {
        private final Set<Entry<String, V>> delegate;

        EntrySet(Set<Entry<String, V>> delegate) {
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public boolean contains(Object o) {
            return this.delegate.contains(o);
        }

        @Override
        public Iterator<Entry<String, V>> iterator() {
            return new EntrySetIterator();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object o) {
            if (this.delegate.remove(o)) {
                removeCaseInsensitiveKey(((Map.Entry<String, V>) o).getKey());
                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            this.delegate.clear();
            caseInsensitiveKeys.clear();
        }

        @Override
        public Spliterator<Entry<String, V>> spliterator() {
            return this.delegate.spliterator();
        }

        @Override
        public void forEach(Consumer<? super Entry<String, V>> action) {
            this.delegate.forEach(action);
        }
    }


    abstract class EntryIterator<T> implements Iterator<T> {
        private final Iterator<Entry<String, V>> delegate;

        private Entry<String, V> last;

        EntryIterator() {
            this.delegate = targetMap.entrySet().iterator();
        }

        Entry<String, V> nextEntry() {
            Entry<String, V> entry = this.delegate.next();
            this.last = entry;
            return entry;
        }

        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @Override
        public void remove() {
            this.delegate.remove();
            if (this.last != null) {
                removeCaseInsensitiveKey(this.last.getKey());
                this.last = null;
            }
        }
    }


    class KeySetIterator extends EntryIterator<String> {
        @Override
        public String next() {
            return nextEntry().getKey();
        }
    }


    class ValuesIterator extends EntryIterator<V> {
        @Override
        public V next() {
            return nextEntry().getValue();
        }
    }


    class EntrySetIterator extends EntryIterator<Entry<String, V>> {
        @Override
        public Entry<String, V> next() {
            return nextEntry();
        }
    }
}