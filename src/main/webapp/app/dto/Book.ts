export class Book {
    constructor(public id: number,
                public bookName: string,
                public authorId: number,
                public description: string,
                public publishingHouse: string,
                public book: File,
                public bookImage: File,
                public rating: number) {
    }
}
