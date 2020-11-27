import { EnumType } from 'typescript';

export class Menus {
    id: number;
    categorytype: string;
    name: string;
    description: string;
    price: number;
    constructor(menus: any) {
        this.id = menus.id;
        this.categorytype = menus.categorytype;
        this.name = menus.name;
        this.price = menus.price;
    }
}
