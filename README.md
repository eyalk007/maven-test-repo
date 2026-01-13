# Maven Parent-Child Dependency Management Test

This repo demonstrates a problem with vulnerability fixing when Maven dependencies use `<dependencyManagement>` inheritance from parent POMs.

## The Scenario

```
parent-project (pom.xml)
├── <dependencyManagement>     ← Versions are DEFINED here
│   └── log4j-core: 2.14.1
│   └── guava: 31.0-jre
│   └── gson: 2.8.5
│   └── commons-io: 2.4
│
└── child-module/pom.xml
    └── <dependencies>         ← Dependencies are DECLARED here (no versions!)
        └── log4j-core         ← Gets version 2.14.1 from parent
        └── guava              ← Gets version 31.0-jre from parent
        └── gson               ← Gets version 2.8.5 from parent
        └── commons-io         ← Gets version 2.4 from parent
```

## The Problem

### 1. Scanner returns wrong location

When xray-scan-lib scans this project, it returns:

```json
{
  "name": "log4j-core",
  "version": "2.14.1",
  "location": "child-module/pom.xml"   // ❌ WRONG - version is not here!
}
```

But the version `2.14.1` is actually defined in `pom.xml` (parent), not `child-module/pom.xml`.

### 2. Frogbot updater fails

When Frogbot tries to fix the vulnerability:

1. It opens `child-module/pom.xml`
2. Looks for `<version>` tag in the log4j dependency
3. **Finds nothing** - child pom has no version tag!
4. Fails with: `dependency org.apache.logging.log4j:log4j-core not found in child-module/pom.xml`

### Actual Error Output

```
19:13:34 [Warn] failed to fix vulnerable dependencies:
failed to update pom.xml files:
child-module/pom.xml: dependency org.apache.logging.log4j:log4j-core not found in child-module/pom.xml
child-module/pom.xml: dependency commons-io:commons-io not found in child-module/pom.xml
child-module/pom.xml: dependency com.google.guava:guava not found in child-module/pom.xml
child-module/pom.xml: dependency com.google.code.gson:gson not found in child-module/pom.xml
```

## Why This Happens

In Maven, `<dependencyManagement>` is a **version catalog** - it defines versions but doesn't add dependencies:

| Section | Purpose | Adds dependency to classpath? |
|---------|---------|------------------------------|
| `<dependencies>` | Declare actual dependencies | ✅ Yes |
| `<dependencyManagement>` | Define versions for children | ❌ No (just a catalog) |

When a child declares a dependency without a version, Maven resolves it from parent's `<dependencyManagement>`. But the scanner only reports where the `<dependency>` tag is, not where the `<version>` is defined.

## The Fix Needed

xray-scan-lib should return **where the version is defined**, not just where the dependency is declared:

```json
{
  "name": "log4j-core", 
  "version": "2.14.1",
  "declaredIn": "child-module/pom.xml",     // Where <dependency> tag is
  "versionDefinedIn": "pom.xml"              // Where <version> comes from ← NEW
}
```

## Vulnerable Dependencies in This Repo

| Dependency | Version | CVE | Version Location |
|------------|---------|-----|------------------|
| log4j-core | 2.14.1 | CVE-2021-44228 (Log4Shell) | `pom.xml` (parent) |
| guava | 31.0-jre | CVE-2020-8908 | `pom.xml` (parent) |
| commons-io | 2.4 | CVE-2021-29425 | `pom.xml` (parent) |
| gson | 2.8.5 | - | `pom.xml` (parent) |

## File Structure

```
.
├── pom.xml                      # Parent POM with <dependencyManagement>
├── child-module/
│   └── pom.xml                  # Child POM with <dependencies> (no versions)
└── README.md
```
