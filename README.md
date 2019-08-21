[![Build Status](https://travis-ci.com/rcsb/ciftools-java.svg?branch=master)](https://travis-ci.com/rcsb/ciftools-java)
[![Changelog](https://img.shields.io/badge/changelog--lightgrey.svg?style=flat)](https://github.com/rcsb/ciftools-java/blob/master/CHANGELOG.md)

# CIFTools
CIFTools implements reading and writing of CIF files ([specification](http://www.iucr.org/resources/cif/spec/version1.1/cifsyntax))
as well as their efficiently encoded counterpart, called BinaryCIF. The idea is to have a robust, type-safe 
implementation for the handling of CIF files which does not care about the origin of the data: both conventional 
text-based and binary files should be handled the same way.

## Getting Started
CIFTools is distributed by maven. To get started, append your `pom.xml` by:
```xml
<dependency>
  <groupId>org.rcsb</groupId>
  <artifactId>ciftools-java</artifactId>
  <version>0.5.4</version>
</dependency>
```

## File Parsing Example

```Java
class Demo {
    public static void main(String[] args) {
        String pdbId = "1acj";
        boolean parseBinary = true;

        // CIF and BinaryCIF are stored in the same data structure
        // to access the data, it does not matter where and in which format the data came from
        CifFile cifFile;
        if (parseBinary) {
            // parse binary CIF from PDBe
            cifFile = CifIO.readFromURL(new URL("http://www.ebi.ac.uk/pdbe/coordinates/" + pdbId + "/full?encoding=bcif"));
        } else {
            // parse CIF from RCSB PDB
            cifFile = CifIO.readFromURL(new URL("https://files.rcsb.org/download/" + pdbId + ".cif"));
        }

        // get first block of CIF
        Block data = cifFile.getFirstBlock();

        // get category with name '_atom_site' from first block - access is type-safe, all categories
        // are inferred from the CIF schema
        AtomSite atomSite = data.getAtomSite();
        FloatColumn cartnX = atomSite.getCartnX();

        // obtain entry id
        String entryId = data.getEntry().getId().get(0);
        System.out.println(entryId);

        // calculate the average x-coordinate - #values() returns as DoubleStream as defined in the
        // schema for column 'Cartn_x'
        OptionalDouble averageCartnX = cartnX.values().average();
        averageCartnX.ifPresent(System.out::println);

        // print the last residue sequence id - this time #values() returns an IntStream
        OptionalInt lastLabelSeqId = atomSite.getLabelSeqId().values().max();
        lastLabelSeqId.ifPresent(System.out::println);

        // print record type - or #values() may be text
        Optional<String> groupPdb = data.getAtomSite().getGroupPDB().values().findFirst();
        groupPdb.ifPresent(System.out::println);
    }
}
```

No difference exists in the API between text-based and binary CIF files. CIF files organize data in blocks, which contain
categories (e.g. `AtomSite`), which contain columns (e.g. `CartnX`), which contain values of a particular type (e.g. 
`double` values representing x-coordinates of atoms). The correct names and types for all defined categories and column 
from the CIF dictionary are provided.

Just as in Mol* implementation, all parsing and decoding is done as lazily as possible. This makes it cheap to acquire 
the data structure and hardly wastes any time on preparing information you will never access. In contrast to 
[MMTF](https://mmtf.rcsb.org/), all data can be accessed if needed.

## Model Creation Example
```Java
class Demo {
    public static void main(String[] args) {
        CifFile cifFile = new CifBuilder()
                // create a block
                .enterBlock("1EXP")
                // create a category with name 'entry'
                .enterEntry()
                // set value of column 'id'
                .enterId()
                // to '1EXP'
                .add("1EXP")
                // leave current column and category
                .leaveColumn()
                .leaveCategory()

                // create atom site category
                .enterAtomSite()
                // and specify some x-coordinates
                .enterCartnX()
                .add(1.0, -2.4, 4.5)
                // values can be unknown or not specified
                .markNextUnknown()
                .add(-3.14, 5.0)
                .leaveColumn()

                // after leaving, the builder is in AtomSite again and provides column names
                .enterCartnY()
                .add(0.0, -1.0, 2.72)
                .markNextNotPresent()
                .add(42, 100)
                .leaveColumn()

                // leaving the builder will release the CifFile instance
                .leaveCategory()
                .leaveBlock()
                .leaveFile();
    }
}
```

A step-wise builder is provided for the creation of `CifFile` instances. It is aware of category and column names and 
the corresponding type described by a column (e.g. the `add` function called above is not overloaded, but rather will 
only accept `String` values while in `entry.id` and only `double` values in `atom_site.Cartn_x`.

## Performance
The implementation can read the full PDB archive (151,579 files) in 2 minutes. This is achieved by lazy decoding and 
parsing - all columns are decoded the first time when they are actually requested. Thus, the parsing overhead is kept 
minimal.

![alt performance](https://raw.githubusercontent.com/rcsb/ciftools-java/master/img/performance.png)

All files are stored in gzip format. The reduced files are either native MMTF files or contain a similar selection of 
CIF categories (i.e. they provide primarily atomic coordinates).

Parsing times were measured on a 3.2 GHz Intel Core i7 machine with 16 GB RAM and a SSD. Executed in parallel with 8 GB 
heap. Performance was measured by JMH using 1 fork, 5 warm-up, and 10 measurement iterations.

## Contributions & Related Projects

- [molstar/ciftools](https://github.com/molstar/ciftools) a TypeScript/JavaScript implementation

These implementations are based on a number of other projects, namely:
- [CIFtools.js](https://github.com/dsehnal/CIFTools.js) by David Sehnal
- [Mol*](https://molstar.github.io) by Alexander Rose and David Sehnal
- [MMTF](https://mmtf.rcsb.org/) by RCSB
