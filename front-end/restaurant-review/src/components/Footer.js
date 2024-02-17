import React from 'react';
import { Flex, Button, Icon, Box } from '@chakra-ui/react';
import { FaArrowUp } from 'react-icons/fa';

function Footer() {
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth', // Добавляем плавный скроллинг
    });
  };

  return (
    <Flex as="footer" align="center" bg="rgba(0, 0, 0, 0.8)" py="4" mt="20">
      <Flex
        maxWidth="1200px"
        width="100%"
        mx="auto"
        justify="space-between"
        alignItems="center"
      >
        <Box ml="4">
          <img src="/resources/logo.svg" alt="Ваш логотип" />
        </Box>

        <Button
          colorScheme="green"
          rightIcon={<Icon as={FaArrowUp} />}
          fontSize="lg"
          fontWeight="medium"
          _hover={{ bg: 'rgba(255, 255, 255, 0.2)' }}
          mr="8"
          onClick={scrollToTop} // Добавляем обработчик события onClick
        >
          Наверх
        </Button>
      </Flex>
    </Flex>
  );
}

export default Footer;
