export default class OrderRepository {
  static getTransaction() {
    const number = `${Date.now()}`;
    const customer = {
      name: 'Javier',
      lastName: 'Perez',
      address: {
        country: 'PER',
        levels: ['150000', '150100', '150101'],
        line1: 'Ca Carlos Ferreyros 180',
        zip: '15036',
      },
      email: 'javier.perez@synapsolutions.com',
      phone: '999888777',
      document: {
        type: 'DNI',
        number: '44112233',
      },
    };
    const shipping = customer;
    const billing = customer;
    return {
      order: {
        number,
        amount: '1.00',
        country: {
          code: 'PER',
        },
        currency: {
          code: 'PEN',
        },
        customer,
        shipping,
        billing,
        products: [
          {
            code: '1234',
            name: 'Llavero',
            quantity: '1',
            unitAmount: '1.00',
            amount: '1.00',
          },
        ],
      },
      settings: {
        brands: ['VISA', 'MSCD'],
        language: 'es_PE',
        businessService: 'MOB',
      }
    };
  }
}
