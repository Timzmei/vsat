
var app = new Vue({
  el: '#app',
  data: {
    message: 'Привет, Vue!'
  }
});

var stations = new Map();

function makeStations(id, name, ip, value, rele, status) {
  return {
    id: id,
    name: name,
    ip: ip,
    value: value,
    rele: rele,
    status: status
  };
}


var messageApi = Vue.resource('/stations{/id}')

Vue.component('message-row', {
  props: ['message'],
  template: '<div><i>({{ message.id }})</i> {{ message.name }} {{ message.ip }} {{ message.value }} {{ message.rele }} {{ message.status }}</div>'
});

//Vue.component('messages-list', {
//  props: ['messages'],
////  template: '<div><message-row v-for="message in messages" v-bind:key="message.id" :message="message" /></div>',
//  template: '<div><table id="Stations" class="table">' +
//                    '<thead>' +
//                      '<tr>' +
//                        '<th>id</th>' +
//                        '<th>Название станции</th>' +
//                        '<th>IP-адрес</th>' +
//                        '<th>EbN0</th>' +
//                        '<th>Оптореле</th>' +
//                        '<th>Status</th>' +
//                        '<th>On/Off</th>' +
//                      '</tr>' +
//                    '</thead>' +
//                    '<tbody>' +
//                      '<tr v-for="message in messages" v-bind:key="message.id">' +
//                        '<td>{{message.id}}</td>' +
//                        '<td>{{message.name}}</td>' +
//                        '<td>{{message.ip}}</td>' +
//                        '<td>{{message.value}}</td>' +
//                        '<td>{{message.rele}}</td>' +
//                        '<td>{{message.status}}</td>' +
//                        '<td><button v-bind:class="message.id" value="off" /></td>' +
//                      '</tr>' +
//                    '</tbody>' +
//                  '</table>' +
//            '</div>',
//  created: function() {
//    messageApi.get().then(result =>
//        {
//            console.log(result)
//            result.json().then(data =>
//                data.forEach(message => this.messages.push(message))
//            )
//        }
//    )
//  }
//})
Vue.component('message-row', {
    props: ['message', 'editMethod', 'messages', 'columnNames'],
    template: '<tr :key="message.id">' +
               '<td v-for="column in columnNames">{{message[column]}}</td>' +
               '<span style="position: absolute">' +
               '<input type="button" value="Wash" @click="wash" />' +
               '</span>' +
               '</tr>',
    methods: {
        edit: function() {
            this.editMethod(this.message);
        },
        del: function() {
            messageApi.remove({id: this.message.id}).then(result => {
                if (result.ok) {
                    this.messages.splice(this.messages.indexOf(this.message), 1)
                }
            })
        },
        wash: function() {
           var message = { text: this.text };
           var id = this.message.id;
           messageApi.update({id}, this.message).then(result =>
           {
               console.log("method wash")
               console.log(result)
//               result.json().then(data => {
//                   console.log(data);
////                   this.messages.splice(data.id, 1, data);
////                   this.text = ''
////                   this.id = ''
//               })
//               console.log(this.text)
//               console.log(this.id)

               console.log(result)
           }


          )
        }
    }
});

Vue.component('messages-list', {
  props: ['messages'],
  data: function() {
    return {
       message: null
    }
  },
  template: '<div><table id="Stations" class="table">' +
                    '<thead>' +
                      '<tr>' +
                        '<th scope="col" v-for="column in columnNames">{{column}}</th>' +
                        '</tr>' +
                        '</thead>' +
                        '<tbody>' +
                        '<message-row v-for="message in messages" :key="message.id" :message="message" ' +
                        ':editMethod="editMethod" :messages="messages" :columnNames="columnNames" />' +
                    '</tbody>' +
                  '</table>' +
            '</div>',
  methods: {
      editMethod: function(message) {
          this.message = message;
      }
    },
  computed: {
        columnNames: function() {
          const names = new Set();
          for (const row of this.messages) {
            for (const key of Object.keys(row)) {
              names.add(key);
            }
          }
          return names;
        }
      },
  created: function() {
    messageApi.get().then(result =>
        {
            console.log(result)
            result.json().then(data =>
                {
//                console.log(data)
                let map = new Map(Object.entries(data));
//                console.log(map)
                map.forEach(message =>
                    {
//                        stations.set(message.id, makeStations(
//                            message.id,
//                            message.name,
//                            message.ip,
//                            message.value,
//                            message.rele,
//                            message.status
//                        ))
                        this.messages.push(message)
                    })
                }
            )
//            console.log(this.messages)
//            console.log(this.st)

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