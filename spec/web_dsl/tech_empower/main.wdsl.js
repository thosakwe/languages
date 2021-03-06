import [App] of './app'

server Frontend {
  mount App on '/'
}

:entry() {
  return Frontend(8080).then(server => {
    print('Listening at http://localhost:/%{server.port}');
  });
}
