import { Menus } from './menus';

export class Order {
    id: number;
    orderNumber: number;
    menus: Menus[];
    constructor(id: number, orderNumber: number, menus: Menus[]) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.menus = menus;
    }
}
