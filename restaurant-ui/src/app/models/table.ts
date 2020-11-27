import { Order } from './order';

export class Table {
    id: number;
    number: number;
    order: Order;
    constructor(id: number, number: number, order: Order) {
        this.id = id;
        this.number = number;
        this.order = order;
    }
}
