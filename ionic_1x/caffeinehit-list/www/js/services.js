var app = angular.module('caffeinehit.services', []);

app.service("YelpService", function ($q, $http, $cordovaGeolocation, $ionicPopup) {
	var self = {
		'page': 1,
		'isLoading': false,
		'hasMore': true,
		'results': [],
		'lat': 51.544440,
		'lon': -0.022974,
		'refresh': function () {
			self.page = 1;
			self.isLoading = false;
			self.hasMore = true;
			self.results = [];
			return self.load();
		},
		'next': function () {
			self.page += 1;
			return self.load();
		},
		'load': function () {
			self.isLoading = true;
			var deferred = $q.defer();

			function loadMap(){
				$cordovaGeolocation.getCurrentPosition({timeout: 1000, enableHighAccuracy: false})
				.then(function(position){
					self.lat = position.coords.latitude;
					self.lon = position.coords.longitude;

					displayResults();

				}, function(err){
					console.log("No location");
					console.log(err);
					$ionicPopup.alert({
					  'title': 'Please switch on geolocation',
					  'template': "It seems like you've switched off geolocation for caffeinehit, please switch it on by going to you application settings."
					});
				})
			};

			function displayResults(){
				var params = {
					page: self.page,
					lat: self.lat,
					lon: self.lon
				};

				$http.get('https://api.codecraft.tv/samples/v1/coffee/', {params: params})
					.success(function (data) {
						self.isLoading = false;
						console.log(data);

						if (data.businesses.length == 0) {
							self.hasMore = false;
						} else {
							angular.forEach(data.businesses, function (business) {
								self.results.push(business);
							});
						}

						deferred.resolve();
					})
					.error(function (data, status, headers, config) {
						self.isLoading = false;
						deferred.reject(data);
					});
			};

			ionic.Platform.ready(function(){
				cordova.plugins.diagnostic.requestRuntimePermissions(function (statuses) {
          for (var permission in statuses) {
            switch (statuses[permission]) {
              case cordova.plugins.diagnostic.permissionStatus.GRANTED:
                console.log("Permission granted to use " + permission);
                loadMap();
                break;
              case cordova.plugins.diagnostic.permissionStatus.NOT_REQUESTED:
                console.log("Permission to use " + permission + " has not been requested yet");
                break;
              case cordova.plugins.diagnostic.permissionStatus.DENIED:
                console.log("Permission denied to use " + permission + " - ask again?");
                break;
              case cordova.plugins.diagnostic.permissionStatus.DENIED_ALWAYS:
                console.log("Permission permanently denied to use " + permission + " - guess we won't be using it then!");
                break;
            }
          }
        }, function (error) {
          console.error("The following error occurred: " + error);
        }, [
          cordova.plugins.diagnostic.runtimePermission.ACCESS_FINE_LOCATION,
          // cordova.plugins.diagnostic.runtimePermission.ACCESS_COARSE_LOCATION
				]);

				displayResults();
			});

			return deferred.promise;
		}
	};

	self.load();

	return self;
});
