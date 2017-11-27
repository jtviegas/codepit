
## HTTP Verbs
  - **POST**
    - Used exclusively for creating new entity instances.  If an instance already exists with the same ID or creating the entity violates a uniqueness criteria, a POST operation _can_ fail.
  - **PUT**
    - PUT operations are used to _replace_ an existing entity.  Hence any optional properties not populated will adopt default values, not retain the values of the replaced entity.  PUT messages tend to be larger as they replace the entire entity.
  - **PATCH**
    - A PATCH HTTP verb is used when an existing property is to be modified.  Hence operations supporting PATCH verbs _only_ need optional properties when an explicit change in the current value is required.

So far, all REST Resource API URLs require a security token.  Hence any of the HTTP verbs listed below can also result in responses commensurate with security issues, such as a **401** "Not Authorized" or **403** "Forbidden".
