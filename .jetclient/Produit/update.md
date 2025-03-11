```toml
name = 'update'
method = 'PUT'
url = 'http://localhost:8080/produit/1'
sortWeight = 5000000
id = '20f50601-f56a-4176-bbfe-b656ab6acd59'

[body]
type = 'JSON'
raw = '''
{
  "id": 1,
  "nom": "Asus Zenbook A14",
  "code": "aza14",
  "description": "Ordinateur portable léger avec processeur Snapdragon X Elite",
  "prix": 1299.99,
  "etat": {
    "id": 1,
    "nom": "neuf"
  },
  "etiquettes": [
    {
      "id": 3,
      "nom": "sqfegrhtj;gegshjhg"
    }
  ]
}'''
```
