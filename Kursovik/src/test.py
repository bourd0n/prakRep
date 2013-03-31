# Import the corpus and functions used from nltk library
import os, sys
from nltk.corpus import brown
from nltk.corpus import cess_esp
from nltk.probability import LidstoneProbDist
from nltk.model import NgramModel
from nltk.tokenize import word_tokenize # Tokenizer

# Tokens contains the words for Genesis and Reuters Trade
#tokens = list(genesis.words('english-kjv.txt'))
if __name__ == "__main__":

#add language
#    tokens = list(brown.words())
#    tokens.extend(list(cess_esp.words()))
#tokens.extend(list(reuters.words(categories = 'trade')))

# estimator for smoothing the N-gram model
#    estimator1 = lambda fdist, bins: LidstoneProbDist(fdist, 0.2)

# N-gram language model with 3-grams

#    model = NgramModel(3, tokens, estimator=estimator1)

# Apply the language model to generate 50 words in sequence
#    text_words = model.generate(50)

    twit = sys.argv[1]

    posVars = sys.argv[2]
    pos = sys.argv[3]
# Concatenate all words generated in a string separating them by a space.
#    text = ' '.join([word for word in text1])
    tokens = word_tokenize(twit)
    tokens2 = word_tokenize(posVars)

    i = 0

    for element in tokens2:
        tokens[int(pos)] = tokens2[i]
        i = i + 1
        print tokens
# print the text
    print '===='
    print twit
    print tokens
    print tokens2
    print ' '.join(tokens2)
#    print text

