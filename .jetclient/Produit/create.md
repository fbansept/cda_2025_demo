```toml
name = 'create'
method = 'POST'
url = 'http://localhost:8080/produit'
sortWeight = 3000000
id = 'd37d2265-bc18-46fc-bb0f-83c5c8a78321'

[body]
type = 'JSON'
raw = '''
{
  "nom" : "fanta",
  "description" : "Une boutille de 1L de fanta",
  "prix" : 1.2,
  "code" : "ftn"
}'''
```
