angular.module('kalahApp', [ 'ngResource' ]).controller('KalahController',
		function($resource, $scope) {
			let
			controller = this;
			controller.game = {};
			let
			Game = $resource('/games/:resourceId?moveId=:moveId', {
				resourceId : '@resourceId'
			}, {
				'update' : {
					params : {
						moveId : function(){return controller.holeIndex;}
					},
					method : 'PUT'
				}
			});
			$scope.newGame = function() {
				Game.save({}, function(game) {
					controller.game = game;
				});
			};
			$scope.play = function(holeIndex) {
				controller.message = undefined;
				controller.holeIndex = holeIndex;
				Game.update({
					resourceId : controller.game.resourceId
				}, {
					moveId : holeIndex
				}, function(game) {
					if (game.isFinished == true){
						controller.message = 'Winner player : ' + game.winner;
						game = undefined;
					}else{
						controller.message = 'Active player : ' + game.activePlayer;
					}
					controller.game = game;
					controller.holeIndex = undefined;
				});
			};
			controller.name = "Test";
		}).config([ '$resourceProvider', function($resourceProvider) {
	// Don't strip trailing slashes from calculated URLs
	$resourceProvider.defaults.stripTrailingSlashes = false;
} ]);