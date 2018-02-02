
// constants

const likePizza=true
console.log('do we like pizza? ', likePizza)

// string templates

firstName = 'Mario'
lastName = 'Puzzo'

console.log(`
his name is: ${firstName} ${lastName}
and he is jolly
`)

// default parameters

var theF = function(name='Justine'){
    console.log(`the name is ${name}`)
};

theF()

// arrow functions

var theF2 = (name,surname) => console.log(`the name is ${name} and surname is ${surname}`)
theF2('Marc', 'Ribot')

var theF3 = (name,surname) => {
    if(!name || !surname)
        throw new Error('check the parameters')
    console.log(`the name is ${name} and surname is ${surname}`)
}
theF3('Marc', 'Andresen')

var tahoe = {
    resorts: ['una', 'montple', 'kioler']
    , print:(delay=1000) => {
        setTimeout(
            () => {console.log(1, this)}
            , delay);
    }
};

tahoe.print();


var tahoe = {
    resorts: ['una', 'montple', 'kioler']
    , print: function(delay=1000){
        setTimeout(
            () => {console.log(2, this)}
            , delay);
    }
};

tahoe.print();

// spread operator
var peaks = ["tallac", "ralston", "rose"]
var canyons = ["ward", "blackwood"]
var tahoe = [...peaks, ...canyons]
console.log(tahoe.join(", "))

console.log(tahoe.reverse().join(", "))

var lakes = ["tahoe", "sintra", "azul"]
var [first, ...rest] = lakes
console.log(rest.join(', '))


var morning = {breakfaslt: "oat", lunch: "omelete"}
var dinner = "cabbage"

var meals = { ...morning, dinner }
console.log(meals)

//promises
/*
const getSpecificData = data => new Promise(
    (resolve, rejects) => {
        const api = `htpps://site.com/${data}`
        const request = new XMLHttpRequest()
        request.open('GET', api)
        request.onload = () => (request.status === 200) ? 
            resolve(JSON.parse(request.response).results):
            reject(Error(request.statusText))
        request.send()
    }
);

getSpecificData('ticks').then(
    data => console.log(data)
    , err => console.error(err)
);
*/
// classes

class Thing {

    constructor(props){
        this.props = props;
    }

    print(){
        console.log(`we were assigned ${this.props}`)
    }

}

const o = new Thing('sdsd');
console.log(o.print()); 

class OtherThing extends Thing {

    constructor(props, data){
        super(props)
        this.data = data
    }

    print(){
        super.print()
        console.log(`we also have ${this.data}`)
    }

}

// modules

    /*
    // in module.js
    export const print = (message) => log(message, new Date())
    export const log = (msg, ts) => console.log(`${ts.toString()}: ${msg}`)

    // in module2.js export a single feature
    const log2 = (msg, ts) => console.log(`${ts.toString()}: ${msg}`)
    export default log2

    //in client code
    //import { print, log } from './module'
    import { print as pt, log  as lg} from './module'
    //import log2 from './module2' 
    import * as m2 from './module2'
    */

// data transformations

    const assorted = ['red', 'ballon', 'nina', 'sixty', 'six', 'berlin', 'blonde'];

    const bs = assorted.filter(o => o.startsWith('b'))
    console.log(bs.join(' '))











