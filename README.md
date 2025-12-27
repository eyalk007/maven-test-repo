# maven-test-repo

Maven test repository for Frogbot integration testing.

## Test Case 1: Simple Vulnerable Dependency ✅

**Status:** ✅ PASSED

**Result:** Frogbot successfully created PR updating `commons-collections:3.2.1` → `3.2.2`

---

## Test Case 2: Property-Based Version

**Status:** 🧪 Ready for testing

**Vulnerable Dependency:**
- `jackson-databind:2.9.8` (referenced via `${jackson.version}` property)
- Known CVEs: Multiple deserialization vulnerabilities
- Fix version: `2.13.0+`

**What to test:**
1. Frogbot should detect the vulnerability in `jackson-databind`
2. Frogbot should update the **property** `<jackson.version>` (not the `<version>` tag)
3. Verify pom.xml property is updated correctly: `<jackson.version>2.9.8</jackson.version>` → `<jackson.version>2.13.x</jackson.version>`

**Expected behavior:**
The updater should detect that the version uses `${jackson.version}` and update the property definition in `<properties>` section.

---

## Future Test Cases (TODO)

- Test Case 3: Parent POM update
- Test Case 4: DependencyManagement
- Test Case 5: Multi-module project
