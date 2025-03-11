```toml
name = 'create'
method = 'POST'
url = 'http://localhost:8080/ligneCommande'
sortWeight = 1000000
id = '9f9b43b7-b9a2-4e7f-b3ad-3004fb85902a'

[body]
type = 'JSON'
raw = '''
{
  "prixVente" : 1500,
  "quantite" : 3,
  "produit" : {
    id: 2
  },
  "commande" : {
    id: 1
  }
}'''
```
