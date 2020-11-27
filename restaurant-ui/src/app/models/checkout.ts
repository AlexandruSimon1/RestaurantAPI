import { Order } from './order';

export class Checkout {
    id: number;
    paymentType: string;
    order: Order;
    constructor(id: number, paymentType: string, order: Order) {
        this.id = id;
        this.paymentType = paymentType;
        this.order = order;
    }
}
