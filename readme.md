# Java Typhoon

Typhoon - but in Java

## Unleashing the typhoon

```
gradle war
gradle jettyRunWar
```

The following endpoints will be available:

```
restlet:http://0.0.0.0:8081/at/unprocessed?restletMethods=GET
restlet:http://0.0.0.0:8081/script/factory?restletMethods=POST
restlet:http://0.0.0.0:8081/script/(id)/run?restletMethods=GET
restlet:http://0.0.0.0:8081/at/(id)?restletMethods=DELETE
restlet:http://0.0.0.0:8081/at/(id)?restletMethods=GET
restlet:http://0.0.0.0:8081/script/(id)?restletMethods=GET
restlet:http://0.0.0.0:8081/at?restletMethods=POST
timer://atq?fixedRate=true&period=30s
direct://apply_template
direct://send_email
```

## Message formats

### At Request

```json
{
  "at":"2014-01-01T12:00:00Z",
  "url": "http://localhost:8081/script/201/run"
}
```

### Script Factory Request

```json
{
  "action": "send_email",
  "data": {
    "name": "Andrew",
    "to": "abraithw@gmail.com",
    "subject": "this is a test..."
  }
}
```

### Example Script

```json
{
  "_id": 201,
  "script": {
    "a": {
      "data": {
        "template": "email",
        "template_data": {
          "name": "Andrew"
        }
      },
      "command": "apply_template"
    },
    "b": {
      "data": {
        "to": "abraithw@gmail.com",
        "subject": "this is a test..."
      },
      "command": "email"
    }
  }
}
```

### Monitoring App

Go to `http://localhost:8080`
