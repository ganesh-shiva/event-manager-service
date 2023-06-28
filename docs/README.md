# Event Manager Service

## Get artist information
API to Get artist information for a specific artistId

HTTP GET: /eventManager/v1/artist/{artistId}

![get](./Get_Artist_Information_by_Artist_Id_HTTP_GET.png)

Sample Request(using postman):

`
http://localhost:8080/eventManager/v1/artist/22
`

Sample Response:
```
{
    "name": "Colosseum",
    "id": "22",
    "imgSrc": "//some-base-url/colosseum.jpg",
    "url": "/colosseum-tickets/artist/22",
    "rank": 2,
    "events": [
        {
            "title": "Blues In Space",
            "id": "2",
            "dateStatus": "singleDate",
            "timeZone": "Europe/London",
            "startDate": "2020-10-18T00:00:00",
            "venue": {
                "name": "O2 Institute2 Birmingham",
                "url": "/o2-institute2-birmingham-tickets-birmingham/venue/42",
                "city": "Birmingham",
                "id": "42"
            },
            "hiddenFromSearch": false
        },
        {
            "title": "A festival Live",
            "id": "7",
            "dateStatus": "singleDate",
            "venue": {
                "name": "O2 Academy Brixton",
                "url": "/o2-academy-brixton/venue/45",
                "city": "London",
                "id": "45"
            },
            "hiddenFromSearch": false
        },
        {
            "title": "Harisson Live",
            "id": "11",
            "dateStatus": "singleDate",
            "venue": {
                "name": "The O2",
                "url": "/o2/venue/44",
                "city": "London",
                "id": "44"
            },
            "hiddenFromSearch": false
        },
        {
            "title": "Huge Live",
            "id": "13",
            "dateStatus": "multiDate",
            "venue": {
                "name": "O2 Academy Sheffield",
                "url": "/o2-academy-sheffield-tickets-sheffield/venue/41",
                "city": "Sheffield",
                "id": "41"
            },
            "hiddenFromSearch": false
        }
    ]
}
```