import axios from "axios";
import Cookies from "universal-cookie";

class Friendship {
    constructor() {
        this.reset();
    }



    set(data) {
        this.source = data.source;
        this.destination = data.destination;
        this.status = data.status;
        this.id = data.id;
    }

    reset() {
        this.id = -1;
    }

}

// Singleton pattern in ES6.
export default (new User);