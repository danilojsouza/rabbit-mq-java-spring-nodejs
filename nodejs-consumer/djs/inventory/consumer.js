import amqp from 'amqplib';
import { RABBITMQ_ADMIN_USER, RABBITMQ_ADMIN_PASSWORD } from './rabbitmq-credentials.js';

const queue = 'PRICE';
const url = `amqp://${RABBITMQ_ADMIN_USER}:${RABBITMQ_ADMIN_PASSWORD}@localhost:5673`;

amqp.connect(url)
    .then(async (connection) => {
        const channel = await connection.createChannel();
        await channel.assertQueue(queue, { durable: true });
        console.log(`[*] Waiting for messages in ${queue}`);

        channel.consume(queue, (msg) => {
            if (msg !== null) {
                const contentType = msg.properties.contentType;
                let body;

                if (contentType === 'application/json') {
                    body = JSON.parse(msg.content.toString());
                } else {
                    body = msg.content.toString();
                }

                console.log(`[x] Received (${contentType}):`, body);
                channel.ack(msg);
            }
        });
    })
    .catch(console.error);
