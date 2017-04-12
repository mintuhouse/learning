// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter', ['ionic', 'ngCordova', 'ionic.service.core', 'ionic.service.push'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

.config(['$ionicAppProvider', function($ionicAppProvider){
  $ionicAppProvider.identify({
    app_id: '33670abd',
    api_key: 'bbdb6e7405030975fed9cdac8a31864c43ae644e35ad191c',
    dev_push: true
  });
}])

.controller('PushCtrl', function($scope, $rootScope, $ionicUser, $ionicPush){
    $scope.identifyUser = function(){
      var user = $ionicUser.get();
      if(!user.user_id){
        user.user_id = $ionicUser.generateGUID();
      }

      angular.extend(user, {
        name: 'hasan',
        bio: 'Author'
      });

      $ionicUser.identify(user).then(function(){
        $scope.identified = true;
        console.log('name: ' + user.name + '--- Id: ' + user.user_id);
      });
    }
})
