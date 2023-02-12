var app = new Vue({
  el: '#app',
  data: {
    message: 'Привет, Vue!'
  }
});


var messageApi = Vue.resource('/stations{/id}')

Vue.component('message-row', {
  props: ['message'],
  template: '<div><i>({{ message.id }})</i> {{ message.name }} {{ message.ip }} {{ message.value }} {{ message.rele }} {{ message.status }}</div>'
});

Vue.component('messages-list', {
  props: ['messages'],
  template: '<div><message-row v-for="message in messages" v-bind:key="message.id" :message="message" /></div>',
  created: function() {
    messageApi.get().then(result =>
        {
            console.log(result)
            result.json().then(data =>
                data.forEach(message => this.messages.push(message))
            )
        }
    )
  }
})

var app7 = new Vue({
  el: '#app-7',
  template: '<messages-list :messages="messages" />',
  data: {
    messages: []
  }
});