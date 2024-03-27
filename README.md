# InsureData-Ingestion-System - a backend java spring-boot application.


# Backend Application Technical Document

## Overview
The backend application serves as the data ingestion system for insurance-related data. It handles the ingestion of organization and employee data through HTTP REST endpoints following RESTful practices. The system ensures data integrity by validating and persisting data into the database. This document outlines the functionalities, data validations, API contracts, and design principles employed in the backend application.

## Functionality

### Endpoints
1. **POST /organisations**: Creates organizations.
2. **POST /organisations/{orgId}/members/upload**: Uploads a CSV file containing employee data for a specific organization. Extracts, validates, and stores the data in the database.
3. **GET /organisations**: Retrieves a paginated list of organizations and their employee details.

### Data Validations
1. **Employee ID**: Must be an integer and unique within the organization.
2. **Email**: Must adhere to standard email format based on RFC. Non-mandatory.
3. **First Name, Middle Name, Last Name**: Can contain spaces and alphabets (lower or upper case), but must have at least 3 alphabetic characters. Middle Name is non-mandatory/nullable.
4. **Date of Birth**: Must be in dd/MM/yyyy format. Mandatory.
5. **Gender**: Must be one of [Male, Female, Other]. Mandatory.

## API Contracts

### POST /organisations
- **Request Body**:
  ```json
  {
    "name": "Organization Name",
    "address": "Organization Address"
  }
  ```
- **Response**:
  - Status: 201 Created
  - Body:
    ```json
    {
      "id": 1,
      "name": "Organization Name",
      "address": "Organization Address"
    }
    ```

### POST /organisations/{orgId}/members/upload
- **Request Body**: CSV File
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "success": true,
      "message": "Data uploaded successfully."
    }
    ```
  - Status: 400 Bad Request (In case of validation failures)
  - Body:
    ```json
    {
      "success": false,
      "errors": [
        {"row": 2, "message": "Invalid email format."},
        {"row": 5, "message": "Date of birth is mandatory."},
        ...
      ]
    }
    ```

### GET /organisations?page=0&size=5
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "organizations": [
        {
          "id": 1,
          "name": "Organization Name",
          "address": "Organization Address",
          "employees": [
            {
              "employee_id": 1,
              "first_name": "Richard",
              "middle_name": null,
              "last_name": "Hendricks",
              "email": "richard.h@gmail.com",
              "date_of_birth": "01/09/1998",
              "gender": "Male"
            },
            ...
          ]
        },
        ...
      ]
    }
    ```

## Design Principles
1. **DRY (Don't Repeat Yourself)**: Code is organized to avoid duplication, promoting reusability and maintainability.
2. **YAGNI (You Aren't Gonna Need It)**: Only essential features are implemented, avoiding unnecessary complexity.
3. **Clean Code**: Codebase is structured and formatted for readability and maintainability.
4. **Design Patterns**: Appropriate design patterns are employed to solve common problems and improve code structure.

## Conclusion
The backend application provides a robust and efficient solution for ingesting insurance-related data. By adhering to RESTful practices, implementing necessary data validations, and following design principles, the system ensures reliability and scalability in handling organizational and employee data.





## API's to run the application:

### Request:

```json
curl --location --request POST 'localhost:8081/api/organisations/create' \
--header 'Content-Type: application/json' \
--data-raw '
{
    "name": "TechGenius",
    "alternate_name": "TechGenius Solutions",
    "description": "TechGenius offers cutting-edge IT solutions for businesses worldwide. From software development to cloud computing, we empower organizations to thrive in the digital age.",
    "location_description": "18th Floor, Tech Tower, Silicon Valley, California, 94025",
    "country": "United States",
    "pin_code": "94025",
    "email": "info@techgenius.com",
    "phone_no": "+12065551234",
    "category": "Information Technology"
}'

```

### Response:

```json
{
    "success": true,
    "status": "SUCCESS",
    "ord_id": 18,
    "name": "TechGenius"
}

```

----------------------------------------------


curl --location --request POST 'localhost:8081/api/organisations/18/members/upload' \
--form 'employee_data=@"/Users/apoorvshamma/Downloads/Employees - Sheet1-2.csv"'



### Response:
``` json
{
    "success": true,
    "status": "Success",
    "ord_id": 18,
    "accepted_employees": [
        {
            "createdAt": "2024-03-26T08:18:52.466",
            "updatedAt": null,
            "id": 1,
            "first_name": "Avi",
            "middle_name": null,
            "last_name": "Mukhija",
            "date_of_birth": "25/07/1998",
            "gender": null,
            "email_id": "17ucs041@lnmiit.ac.in",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.505",
            "updatedAt": null,
            "id": 2,
            "first_name": "Gautam",
            "middle_name": null,
            "last_name": "Bhatia",
            "date_of_birth": "25/08/1992",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.513",
            "updatedAt": null,
            "id": 3,
            "first_name": "Aman",
            "middle_name": null,
            "last_name": "Raheja",
            "date_of_birth": "25/08/1992",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.52",
            "updatedAt": null,
            "id": 4,
            "first_name": "Avni",
            "middle_name": null,
            "last_name": "Kapoor",
            "date_of_birth": "25/08/1998",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.527",
            "updatedAt": null,
            "id": 5,
            "first_name": "Isha ",
            "middle_name": null,
            "last_name": "Bishnoi",
            "date_of_birth": "25/08/1998",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.539",
            "updatedAt": null,
            "id": 6,
            "first_name": "Meghna",
            "middle_name": null,
            "last_name": "Roy",
            "date_of_birth": "25/08/1998",
            "gender": "female",
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.546",
            "updatedAt": null,
            "id": 7,
            "first_name": "Abhishek",
            "middle_name": null,
            "last_name": "Nagpal",
            "date_of_birth": "25/08/1992",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.552",
            "updatedAt": null,
            "id": 8,
            "first_name": "Rajat",
            "middle_name": null,
            "last_name": "Bhatia",
            "date_of_birth": "25/08/1992",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.558",
            "updatedAt": null,
            "id": 9,
            "first_name": "Ayan",
            "middle_name": null,
            "last_name": "Bhatia",
            "date_of_birth": "25/08/1992",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.57",
            "updatedAt": null,
            "id": 10,
            "first_name": "Apoorv",
            "middle_name": null,
            "last_name": "Bhatia",
            "date_of_birth": "25/08/1991",
            "gender": "male",
            "email_id": "richard@gmail.com",
            "org_id": 18
        },
        {
            "createdAt": "2024-03-26T08:18:52.575",
            "updatedAt": null,
            "id": 11,
            "first_name": "Vinish",
            "middle_name": "Kumar",
            "last_name": "Thanai",
            "date_of_birth": "25/08/1992",
            "gender": null,
            "email_id": "richard@gmail.com",
            "org_id": 18
        }
    ],
    "rejected_employees": []
}
```
-------------------------------------------------------------------

### Request :

curl --location --request GET 'localhost:8081/api/organisations/get_paginated_data?page=1&size=5'


### Response :

``` json
{
    "success": true,
    "status": "SUCCESS",
    "org_wide_employees": [
        {
            "org_id": 18,
            "no_of_employees": 5,
            "employees": [
                {
                    "createdAt": "2024-03-26T08:18:52.539",
                    "updatedAt": null,
                    "id": 6,
                    "first_name": "Meghna",
                    "middle_name": null,
                    "last_name": "Roy",
                    "date_of_birth": "25/08/1998",
                    "gender": "female",
                    "email_id": "richard@gmail.com",
                    "org_id": 18
                },
                {
                    "createdAt": "2024-03-26T08:18:52.546",
                    "updatedAt": null,
                    "id": 7,
                    "first_name": "Abhishek",
                    "middle_name": null,
                    "last_name": "Nagpal",
                    "date_of_birth": "25/08/1992",
                    "gender": null,
                    "email_id": "richard@gmail.com",
                    "org_id": 18
                },
                {
                    "createdAt": "2024-03-26T08:18:52.552",
                    "updatedAt": null,
                    "id": 8,
                    "first_name": "Rajat",
                    "middle_name": null,
                    "last_name": "Bhatia",
                    "date_of_birth": "25/08/1992",
                    "gender": null,
                    "email_id": "richard@gmail.com",
                    "org_id": 18
                },
                {
                    "createdAt": "2024-03-26T08:18:52.558",
                    "updatedAt": null,
                    "id": 9,
                    "first_name": "Ayan",
                    "middle_name": null,
                    "last_name": "Bhatia",
                    "date_of_birth": "25/08/1992",
                    "gender": null,
                    "email_id": "richard@gmail.com",
                    "org_id": 18
                },
                {
                    "createdAt": "2024-03-26T08:18:52.57",
                    "updatedAt": null,
                    "id": 10,
                    "first_name": "Apoorv",
                    "middle_name": null,
                    "last_name": "Bhatia",
                    "date_of_birth": "25/08/1991",
                    "gender": "male",
                    "email_id": "richard@gmail.com",
                    "org_id": 18
                }
            ]
        }
    ]
}
```

