(function() {

  angular
    .module('messenger', [
      'ngAnimate',
      'ngMaterial'
    ])
    .config(function($mdThemingProvider) {
      $mdThemingProvider
        .theme('default')
        .primaryPalette('blue')
        .accentPalette('red');

      $mdThemingProvider
        .theme('toolbars')
        .primaryPalette('grey', {
          default: '100'
        })
        .accentPalette('blue-grey');
    })
    .controller('MessengerController', function($window, $http, $timeout) {
      var self = this;
      var scrollabelPanel = document.querySelector('.scrollable');

      this.channel = '';
      this.channelId = '';
      this.messages = [];
      this.currentUser = '';
      this.currentChannel = 'General';
      this.currentChannelIcon = 'message';

      this.scrollPanel = function() {
        $timeout(function() {
          scrollabelPanel.scrollTop = scrollabelPanel.scrollTop = scrollabelPanel.scrollHeight;
        });
      };

      this.changeChannel = function(channel, icon) {
        this.currentChannel = channel;
        this.currentChannelIcon = icon;

        this.scrollPanel();
      };

      this.sendMessage = function(message) {
        $http({
          method: 'POST',
          url: '/message',
          params: {
            from: this.currentUser,
            channel: this.currentChannel,
            body: message,
            channelId: self.channelId
          }
        });
      };

      $http
        .post('/login')
        .then(function(response) {
          self.channelId = response.data.channelId;
          self.channel = new $window.goog.appengine.Channel(self.channelId);

          self.channel.open({
            onmessage: function(message) {
              self.messages.push(JSON.parse(message.data));

              self.scrollPanel();
            },
            onopen: function() {
              console.log('onopen', arguments);
            },
            onerror: function() {
              console.log('onerror', arguments);
            },
            onclose: function() {
              console.log('onclose', arguments);
            }
          });

          return $http.get('/login');
        })
        .then(function(response) {
          self.messages = response.data.messages;

          self.scrollPanel();
        });
    });

})();
