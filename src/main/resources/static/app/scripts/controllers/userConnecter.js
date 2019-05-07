app
.factory('userService',['$resource', function ($resource) {
    return $resource('http://localhost:8080/user',  {}, {
        query: { method: "GET", isArray: true },
        create: { method: "POST"},
        get: { method: "GET" },
        remove: { method: "DELETE" },
        update: { method: "PUT"}
    });

}]);
