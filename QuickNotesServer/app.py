from flask import Flask, Response, request
import json
from werkzeug.security import generate_password_hash, check_password_hash
from models import *
import secrets
from playhouse.shortcuts import model_to_dict, dict_to_model

app = Flask(__name__)


@app.route('/')
def hello_world():
    return Response('Hello World!', status=200)

@app.route('/register', methods=["POST"])
def register():
    data = json.loads(request.data)
    username = data["username"]
    password = data["password"]
    if AppUsers.select().where(AppUsers.username == username).exists():
        return Response("User already registered", status=409)
    else:
        hashed_password = generate_password_hash(password)
        token = secrets.token_hex(16)
        AppUsers.create(username=username, password=hashed_password, token=token)
        return Response("User created succesfully", status=201)

@app.route('/login', methods=["POST"])
def login():
    data = json.loads(request.data)
    username = data["username"]
    password = data["password"]

    user_from_db = AppUsers.select().where(AppUsers.username == username)
    if user_from_db.exists():
        user = user_from_db.get()
        if check_password_hash(user.password, password):
            return Response(user.token, status=201)
        else:
            return Response("Wrong password", status=400)
    else:
        return Response("No such user", status=400)

@app.route('/get_notes', methods=["POST"])
def get_notes():
    token = request.data
    user = AppUsers.select().where(AppUsers.token == token)
    if user.exists():
        user_id = user.get().id
        notes = Note.select().where(Note.author == user_id)
        if not notes.exists():
            return Response("[]", status=200)
        else:
            notes_list = [model_to_dict(note) for note in notes]
            res = [{key: notes_list[i].get(key) for key in notes_list[i].keys() if key not in ["id", "author"]} for i in range(len(notes_list))]
            return Response(json.dumps(res), status=200)
    else:
        return Response("Wrong token", status=400)

@app.route('/post_notes', methods=["POST"])
def post_notes():
    data = json.loads(request.data.decode('utf-8'))
    token = data.get("token")
    notes = data.get("notes")
    user = AppUsers.select().where(AppUsers.token == token)
    if user.exists():
        user_id = user.get().id
        filled_notes = []
        for note in notes:
            new_note = note
            new_note.update({"author": user_id})
            if not Note.select().where(Note.author == user_id, Note.note_id == new_note["note_id"]).exists():
                filled_notes.append(new_note)
        with conn.atomic():
            Note.insert_many(filled_notes).execute()
        return Response("OK", status=200)
    else:
        return Response("Wrong token", status=400)

if __name__ == '__main__':
    app.run()
