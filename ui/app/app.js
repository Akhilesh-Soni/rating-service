var appModule = angular.module('AppModule', ['ngResource']);
appModule.controller('appController', 
    ['$scope', 'ratingService', 'updateRatingService', 'getRatingService',
        function($scope, ratingService, updateRatingService, getRatingService) {
            ratingService.get().$promise.then(function(response) {
                $scope.shows = response;
            });

            $scope.updateRating = function(show){
                updateRatingService.save({
                    showName: show.name,
                    showType: show.type,
                    rating: show.rating
                }).$promise.then(function(){
                    getRatingService.get({show_name: show.name}).$promise
                        .then(function(response) {
                            show.totalVotes = response.totalVotes;
                            show.averageRating = response.averageRating;
                            show.rating = undefined;
                        });
                }, function() {
                    console.log('Internal server error.')
                });
            } 
}]);

appModule.service('ratingService', ['$resource', function($resource){
    return $resource('/rating-service/all-show-ratings', {}, {});
}]);

appModule.service('updateRatingService', ['$resource', function($resource){
    return $resource('/rating-service/update-rating', {}, {});
}]);

appModule.service('getRatingService', ['$resource', function($resource){
    return $resource('/rating-service/show-name/:show_name', {}, {});
}]);