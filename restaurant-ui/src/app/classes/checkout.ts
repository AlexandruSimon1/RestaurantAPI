import { Order } from './order';

export class Checkout {
    id: number;
    paymentType: string;
    orders: Order[];
    constructor(id: number, paymentType: string, orders: Order[]) {
        this.id = id;
        this.paymentType = paymentType;
        this.orders = orders;
    }
}
