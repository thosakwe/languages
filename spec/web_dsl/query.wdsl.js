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
  
  // Assuming you are instantiating with DI:
  //
  // If no `db` is provided, the registered singleton
  // will be used, or one will be instantiated via DI.
  //
  // If you DO provide a `db`, and there is none registered
  // already, then it will become the global singleton.
  constructor App(@inject this._db);

  // You can call a type, have it DI-injected
  // instead of `new`
  "/books" => DbEndpoint('books')
}

:entry() {
  mongo.connect('mongodb://localhost:27017/hello_world').then(db => {
    return serve(App(db)).then(server => {
      print('Listening at http://localhost:%{server.port}');
    });
  });
}
