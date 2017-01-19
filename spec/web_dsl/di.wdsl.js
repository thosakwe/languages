import 'log'

proto ILoggable {
  let _logger:Logger;
  
  constructor ILoggable(@inject this._logger);
  
  :error(e) {
    _logger.error('Oops: %e');
  }
}

// Use `singleton` directive to auto-create a singleton
// of a prototype.
//
// You must provide a static `singleton`.
@singleton
proto SomeSingleton {
  let hello:String;
  
  constructor SomeSingleton(this.hello);
  
  static singleton():SomeSingleton => new SomeSingleton('world');
}

server App : ILoggable {
  route "/greet/:id{[0-9]+}" (@parse id:int, @inject singleton:SomeSingleton) {
    ret { message: singleton.hello };
  }
}

:entry() {
  @inject let logger = new Logger('my-app:foo');
  
  // Invoking the `App` type will run the constructor, with DI
  // args injected.
  //
  // Using `new` requires all args to be provided, and creates a
  // completely fresh instance.
  
  ret serve(App()).then(server => {
    print('Listening at http://localhost:${server.port}');
  });
}
