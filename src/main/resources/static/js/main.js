//var messageApi = Vue.resource('{/id}');
//
//Vue.component('message-row', {
//    props: ['message'],
//    template: '<div><i>({{ message.id }})</i> {{ message.text }})</div>',
//});
//
//Vue.component('messages-list', {
//  props: ['messages'],
////  data: function() {
////    return {
////        message: null
////    }
////  },
//  template:
//    '<div>' +
//        '<message-row v-for="message in messages" :key="message.name" :message="message" ' +
//    '</div>',
//  created: function() {
//    messageApi.get().then(result =>
//        result.json().then(data =>
//            data.forEach(message => this.messages.push(message))
//    )
//  },
////  methods: {
////    editMethod: function(message) {
////        this.message = message;
////    }
//  }
//});

//var app = new Vue({
//  el: '#app',
////  template: '<messages-list :messages="messages" />',
//  data: {
////    message: []
//    message: 'Hello'
//  }
//});


var app2 = new Vue({
  el: '#app-2',
  data: {
    message: 'Вы загрузили эту страницу: ' + new Date().toLocaleString()
  }
});

var app3 = new Vue({
  el: '#app-3',
  data: {
    seen: true
  }
});

var app4 = new Vue({
  el: '#app-4',
  data: {
    todos: [
      { text: 'Изучить JavaScript' },
      { text: 'Изучить Vue' },
      { text: 'Создать что-нибудь классное' }
    ]
  }
});

Vue.component('stations-item', {
  props: ['stations'],
  template: '<li>{{ stations.text }}</li>'
});

var app7 = new Vue({
  el: '#app-7',
  data: {
    groceryList: [
      { id: 0, text: 'Овощи' },
      { id: 1, text: 'Сыр' },
      { id: 2, text: 'Что там ещё люди едят?' }
    ]
  }
});