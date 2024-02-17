import React from 'react';
import { Box, Flex, Button, IconButton, Tooltip } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { AddIcon } from '@chakra-ui/icons';

function Header() {
  return (
    <Flex as="header" align="center" bg="green.400" pt="4" pb="4" color="white">
      <Flex
        maxWidth="1200px"
        width="100%"
        mx="auto"
        justify="space-between"
        alignItems="center"
      >
        <Link to="/" style={{ textDecoration: 'none' }}>
          <Box ml="4">
            <img src="/resources/logo.svg" alt="Ваш логотип" />
          </Box>
        </Link>

        <Flex align="center" mr="8">
          <Link to="/" style={{ textDecoration: 'none' }}>
            <Button
              variant="ghost"
              color="white"
              fontWeight="normal"
              fontSize={'lg'}
              _hover={{ bg: 'rgba(255,255,255,0.2)' }}
              mr="4"
            >
              Главная
            </Button>
          </Link>
          <Link to="/catalog" style={{ textDecoration: 'none' }}>
            <Button
              variant="solid"
              color="green.400"
              fontWeight="normal"
              fontSize={'lg'}
              _hover={{ bg: 'rgba(255,255,255,0.2)', color: 'white' }}
              mr="4"
            >
              Каталог ресторанов
            </Button>
          </Link>
          <Link to="/catalog/add" style={{ textDecoration: 'none' }}>
            <Tooltip
              hasArrow
              label="Добавьте свой ресторан в наш каталог!"
              aria-label="A tooltip"
            >
              <IconButton
                variant="outline"
                aria-label="Send email"
                icon={<AddIcon />}
                color="white"
                _hover={{ bg: 'white', color: 'green.400' }}
              />
            </Tooltip>
          </Link>
        </Flex>
      </Flex>
    </Flex>
  );
}

export default Header;
