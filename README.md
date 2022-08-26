# POC reactive

Proof of concept reactive project


## API Reference

#### Get all resources

```http
  GET /idNames
```

#### Get all resources as stream data

```http
  GET /idNames/stream
```

#### Get all deleted resources as stream data

```http
  GET /idNames/previous/stream
```

#### Get a resource

```http
  GET /idNames/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### create a resource

```http
  POST /idNames
```

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `name`    | `string` | **Required**. A free alphanumeric text |

#### Alter a resource by reference

```http
  PUT /idNames/${id}
```

| Parameter | Type     | Description                        |
| :-------- | :------- | :--------------------------------- |
| `id`      | `string` | **Required**. Id of item to modify |
| `name`    | `string` | **Required**. new value for attribute `name` |

#### Delete a resource by reference

```http
  DELETE /idNames/${id}
```

| Parameter | Type     | Description                        |
| :-------- | :------- | :--------------------------------- |
| `id`      | `string` | **Required**. Id of item to remove |

## License

[MIT](https://gitlab.com/jesid-acosta/poc-reactive/-/blob/main/LICENSE)

