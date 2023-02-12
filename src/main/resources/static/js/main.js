var app = new Vue({
  el: '#app',
  data: {
    message: 'Привет, Vue!'
  }
});


var messageApi = Vue.resource('/{/id}')

Vue.component('message-row', {
  props: ['message'],
  template: '<div><i>({{ message.id }})</i> {{ message.text }}</div>'
});

Vue.component('messages-list', {
  props: ['messages'],
  template: '<div><message-row v-for="message in messages" v-bind:key="message.id" :message="message" /></div>'
})

var app7 = new Vue({
  el: '#app-7',
  template: '<messages-list :messages="messages" />',
  data: {
    messages: [
      { id: 0, text: 'Овощи' },
      { id: 1, text: 'Сыр' },
      { id: 2, text: 'Что там ещё люди едят?' }
    ]
  }
})