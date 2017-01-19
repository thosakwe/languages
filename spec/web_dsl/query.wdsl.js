import 'db';
import 'mongo';

// DB-agnostic
proto DbEndpoint : Rest {
  mut _collection:Table;
  
  DbEndpoint(collection:string, @inject db:Database) {
    _collection = db.table(collection);
  }
  
  :read(id:string) => _collection.read(id);
}

server App {
  let _db:Database;
  
  // Copied from Dart, but hey, DI!
  constructor App(@inject this._db);

  // You can call a type, have it DI-injected
  // instead of `new`
  "/books" => DbEndpoint('books')
}

:entry() {
  mongo.connect('mongodb://localhost:27017/hello_world').then(db => {
    return serve(new App(db), 3000).then(server => {
      print('Listening at http://localhost:%{server.port}');
    });
  });
}
