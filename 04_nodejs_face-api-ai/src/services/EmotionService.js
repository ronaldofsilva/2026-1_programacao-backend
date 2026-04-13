// Nececessário instalar 
// npm install face-api.js canvas

import faceapi from 'face-api.js';
import canvas from 'canvas';

const { Canvas, Image, ImageData } = canvas;

faceapi.env.monkeyPatch({ Canvas, Image, ImageData });


class EmotionService {

    // Chama a api face-api para detecar as emoções da face passada por parâmetro
    async detect(face) {
        await faceapi.nets.ssdMobilenetv1.loadFromDisk('./model-ai');
        await faceapi.nets.faceExpressionNet.loadFromDisk('./model-ai');
        const img = await canvas.loadImage(face);
        const detections = await faceapi
            .detectSingleFace(img)
            .withFaceExpressions();

        return detections.expressions;
    }
    // Formata a saída para percentuais
    // Essa rotina pode ser colocada em um helper
    // Helper é um pattern que consiste consiste em funções ou classes auxiliares projetadas 
    // para realizar pequenas tarefas repetitivas e independentes em todo o projeto, 
    // como formatação de dados, manipulação de texto ou validação
    formatOutput(values){
        const percentuais = {};
        for (let v in values) {
            percentuais[v] = (values[v] * 100).toFixed(2) + '%';
        }
        return percentuais;
    }
}
export default new EmotionService();