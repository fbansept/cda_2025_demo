```toml
name = 'update'
method = 'PUT'
url = 'http://localhost:8080/produit/'
sortWeight = 5000000
id = '20f50601-f56a-4176-bbfe-b656ab6acd59'

[body]
type = 'JSON'
raw = '''
{
  "id":2,
  "nom":"orangina !!!!!!!!!",
  "code":"org",
  "description": "Une bouteille de 1L d'orangina",
  "prix":2.1
}'''
```
