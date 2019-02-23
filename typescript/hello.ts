class Student {
    fullName: string;
    constructor(public firstName: string, public middleInitial: string, public lastName: string) {
        this.fullName = firstName + " " + middleInitial + " " + lastName;
    }
}

interface Person {
    firstName: string;
    lastName: string;
}

function greeter(person : Person) {
    return "Hello, " + person.firstName + " " + person.lastName;
}

let user = new Student("Jane", "M.", "User");

// document.body.innerHTML = greeter(user);

let a = {} as any ;
a.red = '1'

class Point {
    constructor(public x, public y) {}
    getDistance(p: Point) {
        let dx = p.x - this.x;
        let dy = p.y - this.y;
        return Math.sqrt(dx ** 2 + dy ** 2);
    }
}
// ...

// Reopen the interface.
interface Point {
    distanceFromOrigin(): number;
}
Point.prototype.distanceFromOrigin = (this: Point) => {
    return this.getDistance(new Point(0,0));
}

const p1 = new Point(0,0);
const p2 = new Point(3,4);
const d = p2.distanceFromOrigin();
console.log(d);
