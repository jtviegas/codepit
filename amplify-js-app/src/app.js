import API, { graphqlOperation } from '@aws-amplify/api'
import PubSub from '@aws-amplify/pubsub';
import { createTodo } from './graphql/mutations'

import awsconfig from './aws-exports';
API.configure(awsconfig);
PubSub.configure(awsconfig);

async function createNewTodo() {
    const todo = { name: "Use AppSync" , description: "Realtime and Offline"}
    return await API.graphql(graphqlOperation(createTodo, { input: todo }))
}

const MutationButton = document.getElementById('MutationEventButton');
const MutationResult = document.getElementById('MutationResult');

MutationButton.addEventListener('click', (evt) => {
    MutationResult.innerHTML = `MUTATION RESULTS:`;
    createNewTodo().then( (evt) => {
        MutationResult.innerHTML += `<p>${evt.data.createTodo.name} - ${evt.data.createTodo.description}</p>`
    })
});

// other imports
import { listTodos } from './graphql/queries'

const QueryResult = document.getElementById('QueryResult');

async function getData() {
    QueryResult.innerHTML = `QUERY RESULTS`;
    API.graphql(graphqlOperation(listTodos)).then((evt) => {
        evt.data.listTodos.items.map((todo, i) =>
            QueryResult.innerHTML += `<p>${todo.name} - ${todo.description}</p>`
        );
    })
}

getData();

// other imports
import { onCreateTodo } from './graphql/subscriptions'

const SubscriptionResult = document.getElementById('SubscriptionResult');

API.graphql(graphqlOperation(onCreateTodo)).subscribe({
    next: (evt) =>{
        SubscriptionResult.innerHTML = `SUBSCRIPTION RESULTS`
        const todo = evt.value.data.onCreateTodo;
        SubscriptionResult.innerHTML += `<p>${todo.name} - ${todo.description}</p>`
    }
});