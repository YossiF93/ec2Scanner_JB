FROM python:3.8-alpine

ARG USER=boto
RUN useradd -ms /bin/bash ${USER}
USER ${USER}

WORKDIR /home/${USER}
COPY . .

RUN mkdir /home/${USER}/.aws && \
    mv config /home/${USER}/.aws && \
    mv credentials /home/${USER}/.aws && \
    pip install -r requirements.txt 

CMD ["python", "ec2Scanner.py"]
