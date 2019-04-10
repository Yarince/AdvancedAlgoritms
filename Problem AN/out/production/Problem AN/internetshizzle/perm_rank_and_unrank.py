# """ Determine the rank (lexicographic index) of a permutation
#     The permutation may contain repeated items
#
#     Written by PM 2Ring 2017.04.03
# """
#
from itertools import groupby
from math import factorial as fac
#
#
# def lexico_permute_string(s):
#     """ Generate all permutations of `s` in lexicographic order """
#     a = sorted(s)
#     n = len(a) - 1
#     while True:
#         yield ''.join(a)
#         for j in range(n - 1, -1, -1):
#             if a[j] < a[j + 1]:
#                 break
#         else:
#             return
#         v = a[j]
#         for k in range(n, j, -1):
#             if v < a[k]:
#                 break
#         a[j], a[k] = a[k], a[j]
#         a[j + 1:] = a[j + 1:][::-1]
#
#
# def perm_rank(target, base):
#     """ Determine the permutation rank of string `target`
#         given the rank zero permutation string `base`,
#         i.e., the chars in `base` are in lexicographic order.
#     """
#     if len(target) < 2:
#         return 0
#     total = 0
#     head, newtarget = target[0], target[1:]
#     for i, c in enumerate(base):
#         newbase = base[:i] + base[i + 1:]
#         if c == head:
#             return total + perm_rank(newtarget, newbase)
#         elif i and c == base[i - 1]:
#             continue
#         total += perm_count(newbase)
#
#
# base = 'abcccdde'
# # print('total number', perm_count(base))
#
# for i, s in enumerate(lexico_permute_string(base)):
#     rank = perm_rank(s, base)
#     assert rank == i, (i, s, rank)
#     # print('{:2} {} {:2}'.format(i, s, rank))
# print('ok')


def perm_count(s):
    """ Count the total number of permutations of sorted sequence `s` """
    n = fac(len(s))
    iterator = groupby(s)
    for _, g in iterator:
        x = sum(1 for u in g)
        n //= fac(x)
    return n


def perm_unrank(rank, base, head=''):
    """ Determine the permutation with given rank of the
        rank zero permutation string `base`.
    """
    if len(base) < 2:
        return head + ''.join(base)

    total = 0
    for i, c in enumerate(base):
        if i < 1 or c != base[i - 1]:
            base_i_ = base[:i]
            i_ = base[i + 1:]
            newbase = base_i_ + i_
            newtotal = total + perm_count(newbase)
            if newtotal > rank:
                return perm_unrank(rank - total, newbase, head + c)
            total = newtotal


# Test

# target = 'a quick brown fox jumps over the lazy dog'
# base = ''.join(sorted(target))
# rank = perm_rank(target, base)
# print(target)
# print(base)
# print(rank)
# print(perm_unrank(rank, base))

base = sorted("OVUFATDPQEAMS")
print(perm_unrank(2164181944, base))
