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

## Test Case 4: DependencyManagement Update ✅

**Status:** ✅ PASSED

**Result:** Frogbot successfully updated `log4j:1.2.17` → `1.2.17-atlassian-0.4` in dependencyManagement section

---

## Test Case 5: Multi-Module Project

**Status:** 🧪 Ready for testing

**Project Structure:**
```
maven-test-repo/
├── pom.xml                    (parent/aggregator)
├── backend/
│   └── pom.xml               (commons-collections:3.2.1 - vulnerable)
└── frontend/
    └── pom.xml               (jackson-databind:2.9.8 - vulnerable)
```

**Vulnerable Dependencies:**
1. **Backend Module (`backend/pom.xml`):**
   - `commons-collections:commons-collections:3.2.1`
   - Fix version: `3.2.2`

2. **Frontend Module (`frontend/pom.xml`):**
   - `com.fasterxml.jackson.core:jackson-databind:2.9.8`
   - Fix version: `2.13.0+`

**What to test:**
1. Frogbot should detect vulnerabilities in BOTH modules
2. Frogbot should identify correct working directories:
   - `backend/` for commons-collections
   - `frontend/` for jackson-databind
3. Frogbot should update the correct pom.xml files:
   - Update `backend/pom.xml` for backend vulnerability
   - Update `frontend/pom.xml` for frontend vulnerability
4. Create PR(s) with both fixes (aggregated or separate)

**Expected behavior:**
This tests the complete end-to-end flow of multi-module Maven projects with multiple working directories - the most common enterprise Maven structure.

---

## Test Summary

| Test Case | Feature | Status |
|-----------|---------|--------|
| 1. Simple Dependency | Direct `<version>` update | ✅ PASSED |
| 2. Property Version | `${property}` resolution | ✅ PASSED |
| 3. Parent POM | Inherited versions | ⚠️ SKIPPED (Engine limitation) |
| 4. DependencyManagement | Centralized versions | ✅ PASSED |
| 5. Multi-Module | Multiple working directories | 🧪 READY |

**Maven Package Updater Coverage: 4/5 scenarios tested (80%)**
