var x = function(name) {
  console.log('x name is: ' + name)
  this.name = name;
  this.i = 0;
}

x.prototype.stuff = function(stuffname) {
  this.i++;
  this.stuffname= stuffname;
  console.log('name is: ' + this.name)
  console.log('stuffname is: ' + this.stuffname)
  console.log('i is: ' + this.i)
}

var y = function(name) {
  x.call(this, name);
  this.color = 'red';
}
y.prototype = Object.create(x.prototype);
y.prototype.constructor = y;

y.prototype.stuff = function(stuffname, extrastuff) {
  x.prototype.stuff.call(this, stuffname);
  this.extrastuff = extrastuff;
  console.log('extrastuff is: ' + this.extrastuff)
}

var o = new y('o');
console.log('');
o.stuff('s', 'xs');
console.log('');
o.stuff('s2', 'xs2');