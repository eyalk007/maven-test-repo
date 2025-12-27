# maven-test-repo

Maven test repository for Frogbot integration testing.

## Test Case 1: Simple Vulnerable Dependency ✅

**Status:** ✅ PASSED

**Result:** Frogbot successfully created PR updating `commons-collections:3.2.1` → `3.2.2`

---

## Test Case 2: Property-Based Version ✅

**Status:** ✅ PASSED

**Result:** Frogbot successfully updated property `<jackson.version>2.9.8</jackson.version>` → `<jackson.version>2.16.0</jackson.version>`

---

## Test Case 3: Parent POM Update ⚠️

**Status:** ⚠️ SKIPPED - Engine Limitation

**Issue:** Engine cannot resolve versions inherited from parent POMs (returns `version: unknown`)

**Requires:** Engine enhancement to fetch and parse parent POMs from Maven repositories

---

## Test Case 4: DependencyManagement Update

**Status:** 🧪 Ready for testing

**Vulnerable Dependency:**
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>  <!-- Vulnerable! -->
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>commons-fileupload</groupId>
        <artifactId>commons-fileupload</artifactId>
        <!-- Version from dependencyManagement -->
    </dependency>
</dependencies>
```

**Known Issues:**
- commons-fileupload 1.3.1 has multiple CVEs (CVE-2016-1000031, CVE-2016-3092)
- Fix version: 1.5+

**What to test:**
1. Frogbot should detect vulnerability in commons-fileupload:1.3.1
2. Frogbot should update version in `<dependencyManagement>` section (NOT in `<dependencies>`)
3. Verify pom.xml is updated: `<version>1.3.1</version>` → `<version>1.5</version>` (in dependencyManagement)

**Expected behavior:**
The updater should detect that the version is managed in `<dependencyManagement>` and update it there, not in the `<dependencies>` section which has no version tag.

---

## Future Test Cases (TODO)

- Test Case 5: Multi-module project
