#!/bin/sh

mysql -u "gooduser" < Tests/ClearDB.sql
mysql -u "gooduser" < $1
