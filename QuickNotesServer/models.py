from peewee import *

conn = PostgresqlDatabase('quicknotes', user='postgres', password='postgres',
                          host='localhost', port=5432)

cursor = conn.cursor()
conn.close()


class BaseModel(Model):
    class Meta:
        database = conn


class AppUsers(BaseModel):
    id = PrimaryKeyField()
    username = CharField()
    password = CharField()
    token = CharField()


class Note(BaseModel):
    id = PrimaryKeyField()
    author = IntegerField()
    note_id = IntegerField()
    title = CharField()
    text = CharField()
    # timestamp = IntegerField()


try:
    conn.connect()
    conn.create_tables([AppUsers, Note])
except InternalError as px:
    print(str(px))
