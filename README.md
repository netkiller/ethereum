# Setup

## MySQL

```
CREATE SCHEMA `artbank` DEFAULT CHARACTER SET utf8mb4 ;

CREATE USER 'artbank'@'%' IDENTIFIED BY 'k0g*gDsUYo_uhx;';
GRANT ALL ON artbank.* TO 'artbank'@'%';

CREATE USER 'artbank'@'127.0.0.1' IDENTIFIED BY 'k0g*gDsUYo_uhx;';
GRANT ALL ON artbank.* TO 'artbank'@'127.0.0.1';

FLUSH PRIVILEGES;

```

## MongoDB

```
use admin;
db.createUser(
   {
     user: "admin",
     pwd: "chen",
     roles: [ "dbAdmin", "dbOwner", "userAdmin" ]
   }
);

use artbank;
db.createUser(
   {
     user: "artbank",
     pwd: "artbank",
     roles: [ "readWrite", "dbAdmin" ]
   }
)
```