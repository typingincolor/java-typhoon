function TyphoonCtrl($scope, $http) {
  $scope.unprocessedTasks = [];

  $scope.getUnprocessedTasks = function() {
    $http.get('http://localhost:8081/at/unprocessed')
      .success(
        function(data, status, headers, config) {
          $scope.unprocessedTasks = data;
        }
      )
      .error(
        function(data, status, headers, config) {
          console.error("data", data);
          console.error("status", status);
        }
      );
  };

  $scope.delete = function(id) {
    $http.delete('http://localhost:8081/at/' + id)
      .success(
        function(data, status, headers, config) {
          $scope.getUnprocessedTasks();
        }
      )
      .error(
        function(data, status, headers, config) {
          console.error("data", data);
          console.error("status", status);
        }
      );
  };

  $scope.init = function() {
    $scope.getUnprocessedTasks();
  };
}
