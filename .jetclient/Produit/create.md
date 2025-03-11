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
  "nom" : "eeee",
  "description" : "Une boutille de 1L de fanta",
  "prix" : 1,
  "code" : "rrr",
  "etat" : {
    "id" : 1
  },
  "etiquettes" : [
    {
      "id" : 1
    },
    {
      "id" : 4
    }
  ]
}'''
```
