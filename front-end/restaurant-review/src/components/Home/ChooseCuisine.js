import { Heading, Flex, Box } from '@chakra-ui/react';
import CuisineCard from './CuisineCard'; // Импортируем компонент CuisineCard

const ChooseCuisine = () => {
  return (
    <Box>
      <Heading as="h1" size="lg" mb="4" mt="12">
        Выбери свою кухню
      </Heading>
      <Flex flexWrap="wrap">
        <CuisineCard
          image={'/resources/cuisines/italy.jpg'}
          caption="Итальянская"
        />
        <CuisineCard
          image={'/resources/cuisines/france.jpg'}
          caption="Французская"
        />
        <CuisineCard
          image={'/resources/cuisines/japan.jpg'}
          caption="Японская"
        />
        <CuisineCard
          image={'/resources/cuisines/mexica.jpg'}
          caption="Мексиканская"
        />
        <CuisineCard
          image={'/resources/cuisines/china.jpg'}
          caption="Китайская"
        />
      </Flex>
    </Box>
  );
};

export default ChooseCuisine;
